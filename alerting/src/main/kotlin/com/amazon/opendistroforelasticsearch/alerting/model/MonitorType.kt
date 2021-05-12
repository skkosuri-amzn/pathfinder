package com.amazon.opendistroforelasticsearch.alerting.model

enum class MonitorType(val type: String) {
    MONITOR("monitor"),
    DETECTOR("detector");
}
