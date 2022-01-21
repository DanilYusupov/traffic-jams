#!/bin/bash

VERSION="1.1.0"
echo "Building Docker container for the app"

cd ..
docker build -t iusupov/traffic-jam:${VERSION} .