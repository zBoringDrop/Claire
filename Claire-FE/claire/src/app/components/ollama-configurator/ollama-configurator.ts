import { Component } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { UserOllamaConfig, DEFAULT_OLLAMA_CONFIG } from '../../types/userollamaconfig.type';
import { UserOllamaConfigService } from '../../services/userollamaconfigservice';
import { HttpErrorResponse } from '@angular/common/http';
import { ProblemDetails } from '../../types/problemdetails';
import { OLLAMA_OPTIONS } from '../../constants/ollama-options-data';
import { toast } from 'ngx-sonner';

@Component({
  selector: 'app-ollama-configurator',
  imports: [ReactiveFormsModule],
  templateUrl: './ollama-configurator.html',
  styleUrl: './ollama-configurator.css',
})
export class OllamaConfigurator {

  constructor(private ollamaConfigService: UserOllamaConfigService) {}

  protected readonly DEFAULT_OLLAMA_CONFIG = DEFAULT_OLLAMA_CONFIG
  protected readonly OPTIONS = OLLAMA_OPTIONS

  settingsForm = new FormGroup({
    id: new FormControl<number | null>(null),
    user_id: new FormControl<number | null>(null),
    use_custom_config: new FormControl<boolean>(false), 

    temperature: new FormControl<number | null>(null),
    top_k: new FormControl<number | null>(null),
    top_p: new FormControl<number | null>(null),
    repeat_penalty: new FormControl<number | null>(null),
    num_ctx: new FormControl<number | null>(null),
    num_predict: new FormControl<number | null>(null),
    num_thread: new FormControl<number | null>(null),
    keep_alive: new FormControl<string | null>(''),
    num_gpu: new FormControl<number | null>(null),
    main_gpu: new FormControl<number | null>(null),
    
    use_mmap: new FormControl<boolean | null>(true), 
    
    seed: new FormControl<number | null>(null)
  });

  get id() {
    return this.settingsForm.get('id');
  }

  get userId() {
    return this.settingsForm.get('user_id');
  }

  get useCustomConfig() {
    return this.settingsForm.get('use_custom_config');
  }

  get temperature() {
    return this.settingsForm.get('temperature');
  }

  get topK() {
    return this.settingsForm.get('top_k');
  }

  get topP() {
    return this.settingsForm.get('top_p');
  }

  get repeatPenalty() {
    return this.settingsForm.get('repeat_penalty');
  }

  get numCtx() {
    return this.settingsForm.get('num_ctx');
  }

  get numPredict() {
    return this.settingsForm.get('num_predict');
  }

  get numThread() {
    return this.settingsForm.get('num_thread');
  }

  get keepAlive() {
    return this.settingsForm.get('keep_alive');
  }

  get numGpu() {
    return this.settingsForm.get('num_gpu');
  }

  get mainGpu() {
    return this.settingsForm.get('main_gpu');
  }

  get useMMap() {
    return this.settingsForm.get('use_mmap');
  }

  get seed() {
    return this.settingsForm.get('seed');
  }

  ngOnInit() {
    this.checkUserConfig()
  }

  checkUserConfig() {
    this.ollamaConfigService.exists().subscribe({
      next: (res: boolean) => {
        console.log("User has already have a configuration: ", res)

        if (res == true) {
          this.getConfigurationRecord()
        } else {
          this.createConfigurationRecord(DEFAULT_OLLAMA_CONFIG)
        }

      },
      error: (err: HttpErrorResponse) => {
        const error = err.error as ProblemDetails
        console.log("Error on check for configuration exists: ", error)
      }
    })
  }

  createConfigurationRecord(config: UserOllamaConfig) {
    this.ollamaConfigService.createNew(config).subscribe({
      next: (res: UserOllamaConfig) => {
        console.log("User standard configuration created: ", res)

        this.setFormValues(res)
      },
      error: (err: HttpErrorResponse) => {
        const error = err.error as ProblemDetails
        console.log("Error on create new configuration: ", error)
      }
    })
  }

  getConfigurationRecord() {
    this.ollamaConfigService.get().subscribe({
      next: (res: UserOllamaConfig) => {
        console.log("Current user configuration: ", res)

        this.setFormValues(res)
      },
      error: (err: HttpErrorResponse) => {
        const error = err.error as ProblemDetails
        console.log("Error getting user actual configuration: ", error)
      }
    })
  }

  updateConfig() {
    const newConfig = this.settingsForm.value as UserOllamaConfig
    console.log("New config defined: ", newConfig)

    this.ollamaConfigService.update(newConfig).subscribe({
      next: (res: UserOllamaConfig) => {
        console.log("User config updated")
        toast.info('Ollama config updated')
      },
      error: (err: HttpErrorResponse) => {
        const error = err.error as ProblemDetails
        console.log("Error updating user configuration: ", error)
        toast.info('Error updating Ollama config')
      }
    })
  }

  setFormValues(config: UserOllamaConfig) {
    this.id?.setValue(config.id ?? null);
    this.userId?.setValue(config.user_id ?? null);
    this.useCustomConfig?.setValue(config.use_custom_config ?? false);

    this.temperature?.setValue(config.temperature ?? null);
    this.topK?.setValue(config.top_k ?? null);
    this.topP?.setValue(config.top_p ?? null);
    this.repeatPenalty?.setValue(config.repeat_penalty ?? null);

    this.numCtx?.setValue(config.num_ctx ?? null);
    this.numPredict?.setValue(config.num_predict ?? null);
    this.numThread?.setValue(config.num_thread ?? null);
    this.keepAlive?.setValue(config.keep_alive ?? null);

    this.numGpu?.setValue(config.num_gpu ?? null);
    this.mainGpu?.setValue(config.main_gpu ?? null);
    this.useMMap?.setValue(config.use_mmap ?? null);

    this.seed?.setValue(config.seed ?? null);
  }

  resetDefaultValues() {
    this.setFormValues(DEFAULT_OLLAMA_CONFIG)
  }

}
