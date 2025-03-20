#!/bin/bash

cp ../target/project-1.0.0-SNAPSHOT.jar ./
docker build -f Dockerfile -t hsbc/project:latest .
