#!/bin/bash
cd /home/kavia/workspace/code-generation/llama-3-finetuning-platform-26223-26232/finetune_llama3_backend
./gradlew checkstyleMain
LINT_EXIT_CODE=$?
if [ $LINT_EXIT_CODE -ne 0 ]; then
   exit 1
fi

