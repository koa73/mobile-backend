#!/bin/bash

cd /usr/local/src/mobile-backend && git pull && mvn package -Ddir=/usr/local/mobile-app/bin/ && cd /usr/local/mobile-app/ && ./restart-ALL.sh
