package com.amazon.opendistroforelasticsearch.alerting.alerts

// sriram: double check about the options.
enum class ResourceType(val type: String) {
    USER("user"),
    CONTAINER("container"),
    AWS_LAMBDA("aws_lambda"),
    AWS_EC2("aws_ec2"),
    VM("vm");
}
