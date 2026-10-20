#!/usr/bin/env bash
set -e

# Change to script directory
cd "$(dirname "$0")"

if [ ! -d "bin" ]; then
    echo "⚠️  'bin/' directory not found. Compiling first..."
    ./compile.sh
fi

java -cp "bin:lib/*" com.campusolx.Main
