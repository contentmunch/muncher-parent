#!/bin/bash
# scripts/install-git-hooks.sh

echo "🔧 Installing Git hooks..."

git config core.hooksPath .githooks

echo "✅ Hooks installed via .githooks/"

