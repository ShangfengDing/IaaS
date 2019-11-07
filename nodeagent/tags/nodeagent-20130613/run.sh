#!/bin/bash

mvn exec:exec -Dexec.executable="java" -Dexec.args="-cp %classpath appcloud.nodeagent.NodeMonitorServer $*"
