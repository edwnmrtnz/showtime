package com.github.edwnmrtnz.showtime

import java.io.File

object TestAssetReader {
    fun read(
        module: String,
        filenameWithExtension: String
    ): String {
        return File("../$module/src/main/assets/$filenameWithExtension")
            .readText(Charsets.UTF_8)
    }

    fun readAsList(
        module: String,
        filenameWithExtension: String
    ): List<String> {
        return File("../$module/src/main/assets/$filenameWithExtension")
            .bufferedReader()
            .readLines()
    }
}
