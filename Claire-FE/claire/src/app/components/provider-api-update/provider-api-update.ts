import { Component, Input, signal } from '@angular/core';
import { UserProviderApiKey, UserProviderApiKeyPreview } from '../../types/aiprovider.type';
import { UserkeyproviderService } from '../../services/userkeyprovider-service';
import { RemoveUnderscorePipe } from '../../pipes/remove-underscore-pipe';
import { LoadingData } from "../loading-data/loading-data";
import { HttpErrorResponse } from '@angular/common/http';
import { ProblemDetails } from '../../types/problemdetails';
import { FormControl, ReactiveFormsModule } from '@angular/forms';
import { GenericResponse } from '../../types/genericresponse.type';
import { AimodelsyncService } from '../../services/aimodelsync-service';

@Component({
  selector: 'app-provider-api-update',
  imports: [RemoveUnderscorePipe, LoadingData, ReactiveFormsModule],
  templateUrl: './provider-api-update.html',
  styleUrl: './provider-api-update.css'
})
export class ProviderApiUpdate {

  isUpdating = signal<boolean>(false)
  isDeleting = signal<boolean>(false)

  @Input() providerInfo: UserProviderApiKeyPreview | undefined

  inputKeyValue = new FormControl('')
  responseAlert = signal<string>("");

  constructor(private userKeyProviderService: UserkeyproviderService,
              private aiModelSyncService: AimodelsyncService
  ) {}

  ngOnInit() {
    if (this.providerInfo?.api_key) {
      this.inputKeyValue.setValue(this.providerInfo?.api_key)
    }
  }

  makeRequest() {
    this.isUpdating.set(true)

    if (this.providerInfo == null) {
      console.log("User provider info is null!")
      this.isUpdating.set(false)
      return
    }

    const request: UserProviderApiKey = {
      id: this.providerInfo.id ?? null,
      user_id: this.providerInfo.user_id,
      provider_id: this.providerInfo.provider_id,
      api_key: this.inputKeyValue.value?.trim() ?? "",
      active: true //this.providerInfo.active ?? false
    };

    if (request.api_key.length < 1) {
      console.log("User provider key is null or too short!")
      this.isUpdating.set(false)
      return
    }

    this.userKeyProviderService.existsUserKey(request.user_id, request.provider_id).subscribe({
      next: (hasKey: boolean) => {
        console.log("User has already an api key: ", hasKey);

        if (hasKey) {
          this.sendUpdateRequest(request);
        } else {
          this.registerNewKey(request);
        }
      },
      error: (err: HttpErrorResponse) => {
        const error = err.error as ProblemDetails
        console.log("Error on sending key update request: ", error);
        this.isUpdating.set(false)
      }
    })

  }

  sendUpdateRequest(request: UserProviderApiKey) {

    console.log("Update key request: ", request);

    this.userKeyProviderService.updateKey(request).subscribe({
      next: (res: GenericResponse) => {
        console.log("Key update successfully")
        this.showTempResponse(res);

        this.syncModels(Number(request.provider_id));
        this.isUpdating.set(false)
      },
      error: (err: HttpErrorResponse) => {
        const error = err.error as ProblemDetails
        console.log("Error on sending key update request: ", error);
        
        const message = err.error?.title ?? err.message ?? "Unknown error";
        this.showTempResponse(message)
        this.isUpdating.set(false)
      }
    })
  }

  registerNewKey(request: UserProviderApiKey) {
    request.id = null;
    console.log("Key registration request: ", request);
    
    this.userKeyProviderService.addNewKey(request).subscribe({
      next: (res: GenericResponse) => {
        console.log("Key registered successfully");
        this.providerInfo!.id = Number(res.status)
        this.showTempResponse(res)

        this.syncModels(Number(request.provider_id));
        this.isUpdating.set(false)
      },
      error: (err: HttpErrorResponse) => {
        const error = err.error as ProblemDetails
        console.log("Error on sending key registration request: ", error);

        const message = err.error?.title ?? err.message ?? "Unknown error";
        this.showTempResponse(message)
        this.isUpdating.set(false)
      }
    })
  }

  deleteKey() {
    this.isDeleting.set(true)

    if (this.providerInfo == null || this.providerInfo.id == null) {
      console.log("Delete failed: resource to delete is null")
      this.isDeleting.set(false)
      return
    }

    this.userKeyProviderService.deleteKey(this.providerInfo.id).subscribe({
      next: (res: GenericResponse) => {
        console.log("Key deleted successfully");
        this.inputKeyValue.reset()
        
        if (this.providerInfo?.id != null) {
          this.providerInfo.id = null;
        }

        this.showTempResponse(res)
        this.isDeleting.set(false)
      },
      error: (err: HttpErrorResponse) => {
        const error = err.error as ProblemDetails
        console.log("Error on sending key delete request: ", error);

        const message = err.error?.title ?? err.message ?? "Unknown error";
        this.showTempResponse(message)
        this.isDeleting.set(false)
      }
    })
  }

  showTempResponse(res: GenericResponse) {
    if (res.status == null || res.status <= 0) {
      this.responseAlert.set("Error");
    } else {
      this.responseAlert.set("Success");
    }
    
    setTimeout(() => {
      this.responseAlert.set("");
    }, 6_000);
  }

  syncModels(providerId: number) {
    this.aiModelSyncService.syncSpecificModelById(providerId).subscribe({
      next: (res: GenericResponse) => {
        console.log("Models database sync successfully: ", res);
      },
      error: (err: HttpErrorResponse) => {
        const error = err.error as ProblemDetails
        console.log("Error on sending key registration request: ", error);

        const message = err.error?.title ?? err.message ?? "Unknown error";
        this.showTempResponse(message)
      }
    })
  }

}
