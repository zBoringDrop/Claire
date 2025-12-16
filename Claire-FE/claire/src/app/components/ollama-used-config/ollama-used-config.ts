import { Component, Input } from '@angular/core';
import { UserOllamaConfig } from '../../types/userollamaconfig.type';
import { CONFIG_GROUPS, OLLAMA_OPTIONS } from '../../constants/ollama-options-data';

@Component({
  selector: 'app-ollama-used-config',
  imports: [],
  templateUrl: './ollama-used-config.html',
  styleUrl: './ollama-used-config.css',
})
export class OllamaUsedConfig {

  @Input() config?: UserOllamaConfig | null

  protected readonly OPTIONS = OLLAMA_OPTIONS
  protected readonly GROUPS = CONFIG_GROUPS;

}
