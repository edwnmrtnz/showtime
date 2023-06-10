package com.github.edwnmrtnz.showtime.core.data

class ApiQueryParamsBuilder {
    private val query: HashMap<String, String> = hashMapOf(
        "language" to "en-US"
    )

    fun adult(include: Boolean): ApiQueryParamsBuilder {
        query["include_adult"] = include.toString()
        return this
    }

    fun video(include: Boolean): ApiQueryParamsBuilder {
        query["video"] = include.toString()
        return this
    }

    fun page(page: Int): ApiQueryParamsBuilder {
        query["page"] = page.toString()
        return this
    }

    fun sortBy(isDesc: Boolean): ApiQueryParamsBuilder {
        query["sort_by"] = "popularity." + if (isDesc) "desc" else "asc"
        return this
    }

    fun build(): HashMap<String, String> {
        return query
    }

    fun clear() = query.clear()
}
