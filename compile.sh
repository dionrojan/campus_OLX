#!/usr/bin/env bash
set -e

# Change to script directory
cd "$(dirname "$0")"

echo "🔨 Compiling Campus OLX Java project..."
mkdir -p bin

javac -cp "lib/*" -d bin $(find src -name "*.java")

echo "✅ Compilation successful! Compiled classes placed in 'bin/' directory."
