export interface OllamaOption {
  label: string;
  description: string;
}

export const OLLAMA_OPTIONS = {
  // General Configuration
  use_custom_config: {
    label: "Enable Custom Configuration",
    description: "Enable this option to bypass the default system presets and apply the specific configuration parameters defined below."
  },

  // Sampling Parameters
  temperature: {
    label: "Temperature",
    description: "Controls the randomness of the output. High values (e.g., 0.8) make the model more creative and unpredictable. Low values (e.g., 0.2) make it more logical, focused, and deterministic."
  },
  top_k: {
    label: "Top-K",
    description: "Limits the token selection to the top K most probable words. Lowering this value (e.g., to 40) helps reduce nonsensical or irrelevant outputs."
  },
  top_p: {
    label: "Top-P",
    description: "Also known as Nucleus Sampling. The model considers the smallest set of tokens whose cumulative probability exceeds P (e.g., 0.9 means the top 90% probability mass)."
  },
  repeat_penalty: {
    label: "Repetition Penalty",
    description: "Discourages the model from repeating the same text. Values higher than 1.0 reduce loops. A value of 1.0 means no penalty is applied."
  },

  // Resources & Dimensions
  num_ctx: {
    label: "Context Size",
    description: "The size of the context window in tokens. This controls how much text ('memory') the model can process at once. Higher values require more VRAM."
  },
  num_predict: {
    label: "Max Predict",
    description: "The maximum number of tokens to generate in a single response. Setting this to -1 allows the model to generate until it stops naturally."
  },
  num_thread: {
    label: "CPU Threads",
    description: "The number of CPU threads allocated for computation. For optimal performance, this should be set to the number of physical cores available on your CPU."
  },
  keep_alive: {
    label: "Keep Alive",
    description: "The duration the model stays loaded in VRAM after a request (e.g., '5m' for 5 minutes). Prevents the delay of reloading the model for subsequent requests."
  },

  // Hardware Acceleration
  num_gpu: {
    label: "GPU Layers",
    description: "The number of model layers to offload to the GPU. Set this to -1 to offload all possible layers for maximum performance."
  },
  main_gpu: {
    label: "Main GPU ID",
    description: "The index of the main GPU to use if multiple GPUs are available in the system (starts at 0)."
  },
  use_mmap: {
    label: "Use MMAP",
    description: "Enables Memory Mapping to load the model directly from disk to memory, speeding up load times but potentially locking file usage."
  },

  // Advanced / Experimental
  seed: {
    label: "Seed",
    description: "A specific number to initialize the random number generator. Using the same seed will result in reproducible outputs for the same prompt."
  }
};

export const CONFIG_GROUPS = [
  {
    title: 'Sampling Parameters',
    icon: 'fi fi-rr-blood-test-tube-alt text-purple-400',
    keys: ['temperature', 'top_k', 'top_p', 'repeat_penalty']
  },
  {
    title: 'Resources & Dimensions',
    icon: 'fi fi-rr-resources text-green-400',
    keys: ['num_ctx', 'num_predict', 'num_thread', 'keep_alive']
  },
  {
    title: 'Hardware Acceleration',
    icon: 'fi fi-rr-rocket-lunch text-red-400',
    keys: ['num_gpu', 'main_gpu', 'use_mmap']
  },
  {
    title: 'Advanced / Experimental',
    icon: 'fi fi-rr-flask text-yellow-400',
    keys: ['seed']
  }
];