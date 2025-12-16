export interface UserOllamaConfig {
  id: number | null;
  user_id: number | null;
  use_custom_config?: boolean;
  
  temperature?: number;
  top_k?: number;
  top_p?: number;
  
  repeat_penalty?: number; 
  
  num_ctx?: number;
  num_predict?: number;
  num_thread?: number;
  num_gpu?: number;
  main_gpu?: number;
  use_mmap?: boolean;
  keep_alive?: string;
  
  seed?: number;
}

export const DEFAULT_OLLAMA_CONFIG: UserOllamaConfig = {
  id: null,
  user_id: null,

  use_custom_config: false,

  temperature: 0.1,
  top_k: 20,
  top_p: 0.2,
  repeat_penalty: 1.1,

  num_ctx: 4096,
  num_predict: -1,
  num_thread: 4,
  keep_alive: '5m',

  num_gpu: -1,
  main_gpu: 0,
  use_mmap: true,

  seed: 42
};