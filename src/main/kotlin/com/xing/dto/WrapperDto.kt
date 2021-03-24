package com.xing.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.xing.services.externals.Metadata
import com.xing.services.helpers.utils.Error

class WrapperDto(
    @JsonProperty("data")
    val responsedto: ResponseDto,
    @JsonProperty("metadata")
    var metadata: Metadata? = null,
    @JsonProperty("errors")
    var errors: List<Error>? = null,
)