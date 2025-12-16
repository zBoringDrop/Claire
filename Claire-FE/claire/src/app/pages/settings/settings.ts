import { Component, signal } from '@angular/core';
import { UserProviderApiKeyPreview } from '../../types/aiprovider.type';
import { UserkeyproviderService } from '../../services/userkeyprovider-service';
import { HttpErrorResponse } from '@angular/common/http';
import { ProblemDetails } from '../../types/problemdetails';
import { ProviderApiUpdate } from "../../components/provider-api-update/provider-api-update";
import { FormsModule } from "@angular/forms";
import { OllamaConfigurator } from "../../components/ollama-configurator/ollama-configurator";

@Component({
  selector: 'app-settings',
  imports: [ProviderApiUpdate, FormsModule, OllamaConfigurator],
  templateUrl: './settings.html',
  styleUrl: './settings.css'
})
export class Settings {

  userProvidersAndKeys = signal<UserProviderApiKeyPreview[]>([])

  constructor(private keyProviderService: UserkeyproviderService) {}

  ngOnInit() {
    this.fillApiSection()
  }

  fillApiSection() {
    this.keyProviderService.getAll().subscribe({
      next: (res: UserProviderApiKeyPreview[]) => {

        this.sortByProviderName(res)

        console.log("User provider keys response: ", res)
        this.userProvidersAndKeys.set(res)
      },
      error: (err: HttpErrorResponse) => {
        const error = err.error as ProblemDetails
        console.log("Error getting user provider keys: ", error)
      }
    })
  }

  sortByProviderName(res: UserProviderApiKeyPreview[]) {
    res.sort((a, b) =>
      a.provider_name.localeCompare(b.provider_name)
    );
  }

}
