package com.mikepenz.storyblok.sdk

import com.mikepenz.storyblok.sdk.http.provideClient
import com.mikepenz.storyblok.sdk.model.*
import io.ktor.client.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.serialization.json.Json

/**
 * This class is the main API class to do all the requests on the Storyblok API.
 *
 * @param token The API token used to do the API requests
 * @param providedClient Provide a custom client implementation, not using the default
 * @param configureClient Creates the [HttpClient]. Overwriting this allows to construct a custom engine, or do further adjustments to the client. Note that it is required to provide a serializer again to parse the data
 */
class Storyblok constructor(
    private val token: String,
    providedClient: HttpClient? = null,
    configureClient: (HttpClientConfig<*>.() -> Unit)? = null
) {

    /**
     * @param token The API token used to do the API requests
     */
    constructor(token: String) : this(token, null, null)

    private val client by lazy {
        (providedClient ?: provideClient()).config {
            (configureClient?.invoke(this) ?: install(JsonFeature) {
                serializer = KotlinxSerializer(Json(builderAction = {
                    ignoreUnknownKeys = true
                    prettyPrint = true
                }))
            })
        }
    }

    /**
     * Defines if the requests shall be made in the edit mode
     */
    var editMode = false

    /**
     * The protocol to use for the requests
     */
    var apiProtocol = API_PROTOCOL

    /**
     * The port to use for the requests
     */
    var apiPort = API_PROTOCOL.defaultPort

    /**
     * The endpoint to call for the requests
     */
    var apiEndpoint = API_ENDPOINT

    /**
     * The current API version to use for the requests
     */
    var apiVersion = API_VERSION

    /**
     * The requestBuilder to modify requests constructed, allows to add additional headers, ...
     */
    private fun requestBuilder(block: HttpRequestBuilder.() -> Unit): HttpRequestBuilder.() -> Unit {
        return {
            // headers
            header("User-Agent", SDK_USER_AGENT)
            // parameters
            parameter("token", token)
            parameter("version", if (editMode) VERSION_DRAFT else VERSION_PUBLISHED)
            block()
        }
    }

    /**
     * Returns a story object for the full_slug, id or uuid if authenticated using a preview or public token.
     *
     * [key]                    :full_slug :: Use the full_slug of your content entry to retrieve it. Attention: If you use field level translations and a root
     *                          folder with the same name of a language code you need to prepend [default] to the path to retrieve the default language
     *                          (Example: api.storyblok.com/v1/cdn/stories/[default]/de/home).
     *                          :id :: Use the numeric id of your content entry to retrieve it
     *                          :uuid :: You can use the uuid property to query for your content entry.
     *                          To tell our API to use the uuid instead of the id append the query param find_by=uuid
     * [findBy]                 Added if you want to query by uuid instead of using the numeric id
     * [resolveLinks]           The parameter resolve_links will automatically resolve internal links of the multilink field type. If the value is story the whole story
     *                          object will be included. If the value is url only uuid, id, name, path, slug and url (url is a computed property which returns the
     *                          "Real path" if defined to use it for navigation links) will be included. The limit of resolved links per Story is 50 when resolving with
     *                          story and 100 when resolving with url.
     * [resolveRelations]       Resolve relationships to other Stories of a multi-option or single-option field-type. Provide the component name and the field key
     *                          as comma separated string. The limit of resolved relationships is 100 Stories.
     *                          Example: resolve_relations=page.author,page.categories; Read more about it in our tutorial.
     * [fromRelease]            Access version of specific release by release id
     * [language]               Add the language i18n code as query parameter to receive a localized version when using a numeric id or uuid as path parameter
     * [fallbackLang]           Define a custom fallback language (i18n code). By default the fallback language is the one defined in the space settings
     * [cv]                     Defines the cache version
     */
    suspend fun fetchStory(
        key: String,
        findBy: String? = null,
        resolveLinks: String? = null,
        resolveRelations: String? = null,
        fromRelease: Long? = null,
        language: String? = null,
        fallbackLang: String? = null,
        cv: Long? = null
    ): Story? {
        val story: StoryWrapper = client.get(buildUrl(ENDPOINT_STORIES, key), requestBuilder {
            parameter("find_by", findBy)
            parameter("resolve_links", resolveLinks)
            parameter("resolve_relations", resolveRelations)
            parameter("from_release", fromRelease)
            parameter("language", language)
            parameter("fallback_lang", fallbackLang)
            parameter("cv", cv)
        })
        return story.story
    }

    /**
     * Returns a list of stories that are in your Storyblok space. The stories are returned in sorted order, depending on the order in your space.
     * You can use the query parameter sort_by with any story object property and first level of your content type to order the response to your needs.
     *
     * If no entries are found with your filters applied, you will receive an empty array. You will not receive a 404 error message, to check if you have
     * results go for the array length.
     *
     * [startsWith]	            Filter by full_slug. Can be used to retrieve all entries form a specific folder. Examples: starts_with=de/beitraege, starts_with=en/posts. Attention: If you use field level translations and a root folder with the same name of a language code you need to prepend [default] to the path to retrieve the default language (Example: starts_with=[default]/de/home).
     * [byUuids]	            Get stories by comma separated uuid. To get a specific language you need to combine this filter with starts_with=en/\* (replace en with your langauge) Example: by_uuids=9aa72a2f-04ae-48df-b71f-25f53044dc97,84550816-245d-4fe6-8ae8-b633d4a328f4
     * [fallbackLang]	        Define a custom fallback language. By default the fallback language is the one defined in the space settings
     * [byUuidsOrdered]	        Get stories by comma separated uuid ordered by the sequence provided in the parameter value. To get a specific language you need to combine this filter with starts_with=en/\* (replace en with your langauge) Example: by_uuids_ordered=9aa72a2f-04ae-48df-b71f-25f53044dc97,84550816-245d-4fe6-8ae8-b633d4a328f4
     * [excludingIds]	        Exclude stories by comma separated numeric ids. Example: excluding_ids=101231,9101231
     * [excludingFields]	    Exclude specific fields of your content type by comma seperated names. Example: excluding_fields=title,content
     * [resolveLinks]	        The parameter resolve_links will automatically resolve internal links of the multilink field type. If the value is story the whole story object will be included. If the value is url only uuid, id, name, path, slug and url (url is a computed property which returns the "Real path" if defined to use it for navigation links) will be included. The limit of resolved links per Story is 50 when resolving with story and 100 when resolving with url.
     * [resolveRelations]	    Resolve relationships to other Stories of a multi-option or single-option field-type. Provide the component name and the field key as comma separated string. The limit of resolved relationships per item is 100 Stories. Example: resolve_relations=page.author,page.categories
     * [fromRelease]	        Access version of specific release by release id
     * [sortBy]	                Sort entries by specific attribute and order with content.YOUR_FIELD:asc and content.YOUR_FIELD:desc. Possible values are all attributes of the entry and all fields of your content type inside content with the dot as seperator. Example: position:desc, content.your_custom_field:asc, content.field_type_xy.field_xy:asc, created_at:desc. If you want to use the sorting provided by the user in the Storyblok admin interface you need to use position:desc. By default all custom fields are sorted as strings. To sort custom fields with numeric values you need to provide the type information (float or int) like following: content.YOUR_FIELD:asc:float or content.YOUR_FIELD:asc:int
     * [searchTerm]	            Search content items by full text.
     * [filterQuery]	        Filter by specific attribute(s) of your content type. The filter query parameter needs to contain the query operation key. Separate the values by a comma , to filter by multiple values.
     *                          filter_query[ATTRIBUTE][OPERATION]=VALUE,...
     *
     *                          Following filter operations OPERATION are available:
     *                              is              - Filter your entries by checking if your custom attribute (any field inside the content field) is of a specific type.
     *                              in              - Filter your entries by checking if your custom attribute (any field inside the content field) has a value that is equal to one of the values provided.
     *                              not_in          - Filter your entries by checking if your custom attribute (any field inside the content field) does not have a value that is equal to one of the values provided.
     *                              like            - Filter your entries by checking if your custom attribute (any field inside the content field) has a value that is "like" the value provided.
     *                              not_like        - Filter your entries by checking if your custom attribute (any field inside the content field) has a value that is "not_like" the value provided.
     *                              in_array        - Filter your entries by checking if your custom array attribute (any field inside the content field) contains one of the values provided. As soon as one of the provided values separated with , are in the array field, the story object will be in the response.
     *                              all_in_array    - Filter your entries by checking if your custom array attribute (any field inside the content field) contains all of the values provided. As soon as all of the provided values separated with , are in the array field, the story object will be in the response.
     *                              gt-date         - Think of it at AFTER a specific date. Allows you to filter fields of type date/datetime (Format: YYYY-mm-dd HH:MM). Returns all entries that are greater (eg. later) than the provided value.
     *                              lt-date         - Think of it at BEFORE a specific date. Allows you to filter fields of type date/datetime (Format: YYYY-mm-dd HH:MM). Returns all entries that are lower than (eg. before) the provided date.
     *                              gt-int          - Allows you to filter fields of type number, string (number value), or custom field type with numbers in the schema. Returns all entries that are GREATER than the provided value.
     *                              lt-int          - Allows you to filter fields of type number, or custom field type with numbers in the schema. Returns all entries that are LOWER than the provided value.
     *                              gt-float        - Allows you to filter fields of type float, string (float value), or custom field type with numbers in the schema. Returns all entries that are GREATER than the provided value.
     *                              lt-float        - Allows you to filter fields of type number, or custom field type with numbers in the schema. Returns all entries that are LOWER than the provided value.
     *
     *                          Checkout the filter_query Examples we put together for you with most common use-cases.
     *
     * [isStartpage]	        Filter by folder startpage. Use is_startpage=1 to only return startpages and is_startpage=0 to exclude startpages from the result.
     * [withTag]	            Filter by specific tag(s). Use comma to filter by multiple tags. Examples: with_tag=featured,home
     * [page]	                Numeric. default: 1. Read more at Pagination
     * [perPage]	            Numeric. default: 25, max: 100. Read more at Pagination
     * [cv]	                    Read more about cache version at Cache invalidation
     */
    suspend fun fetchStories(
        startsWith: String? = null,
        byUuids: Array<String>? = null,
        fallbackLang: String? = null,
        byUuidsOrdered: Array<String>? = null,
        excludingIds: IntArray? = null,
        excludingFields: Array<String>? = null,
        resolveLinks: String? = null,
        resolveRelations: Array<String>? = null,
        fromRelease: Long? = null,
        sortBy: Array<String>? = null,
        searchTerm: String? = null,
        filterQuery: String? = null,
        isStartpage: Boolean? = null,
        withTag: Array<String>? = null,
        page: Int = 1,
        perPage: Int = 25,
        cv: Long? = null
    ): List<Story> {
        val storyList: StoriesWrapper = client.get(buildUrl(ENDPOINT_STORIES), requestBuilder {
            parameter("starts_with", startsWith)
            parameter("by_uuids", byUuids)
            parameter("fallback_lang", fallbackLang)
            parameter("by_uuids_ordered", byUuidsOrdered)
            parameter("excluding_ids", excludingIds)
            parameter("excluding_fields", excludingFields)
            parameter("resolve_links", resolveLinks)
            parameter("resolve_relations", resolveRelations)
            parameter("from_release", fromRelease)
            parameter("sort_by", sortBy)
            parameter("search_term", searchTerm)
            parameter("filter_query", filterQuery)
            parameter("is_startpage", isStartpage)
            parameter("with_tag", withTag)
            parameter("page", page)
            parameter("per_page", perPage)
            parameter("cv", cv)
        })
        return storyList.stories ?: emptyList()
    }

    /**
     * Returns the current space object, if you're authenticated with a token.
     */
    suspend fun fetchCurrentSpace(): Space? {
        val space: SpaceWrapper = client.get(buildUrl(ENDPOINT_SPACE), requestBuilder {})
        return space.space
    }

    /**
     * Returns an array of datasource objects.
     *
     * [page]       Numeric. default: 1. Read more at Pagination
     * [perPage]    Numeric. default: 25, max: 1000. Read more at Pagination
     */
    suspend fun fetchDatasources(page: Int = 1, perPage: Int = 25): List<Datasource> {
        val datasourceList: DatasourceWrapper = client.get(buildUrl(ENDPOINT_DATASOURCE), requestBuilder {
            parameter("page", page)
            parameter("per_page", perPage)
        })
        return datasourceList.datasources ?: emptyList()
    }

    /**
     * Returns an array of datasource entry objects for the datasource and dimension defined, if authenticated using a preview or public token.
     *
     * [datasource] Datasource group id/slug
     * [dimension]  Dimension that you defined for your datasource (eg. dimension=en)
     * [page]       Numeric. default: 1. Read more at Pagination
     * [perPage]    Numeric. default: 25, max: 1000. Read more at Pagination
     * [cv]         Defines the cache version
     */
    suspend fun fetchDatasourceEntries(
        datasource: String? = null,
        dimension: String? = null,
        page: Int = 1,
        perPage: Int = 25,
        cv: Long? = null
    ): List<Datasource> {
        val datasourceList: DatasourceWrapper = client.get(buildUrl(ENDPOINT_DATASOURCE_ENTRIES), requestBuilder {
            parameter("datasource", datasource)
            parameter("dimension", dimension)
            parameter("page", page)
            parameter("per_page", perPage)
            parameter("cv", cv)
        })
        return datasourceList.datasources ?: emptyList()
    }

    /**
     * Returns the links object containing all links of one space. Use the version parameter and the correct token types to receive either draft and published
     * or only published links.
     *
     * [startsWith]     Filter by full_slug. Can be used to retrieve all links form a specific folder. Examples: starts_with=de/beitraege, starts_with=en/posts
     * [paginated]      Set this to 1 if you want to retrieve the paginated results. With the parameters per_page and page you can define the page size and page number
     * [page]           Numeric. default: 1. Read more at Pagination
     * [perPage]        Numeric. default: 25, max: 1000. Read more at Pagination
     */
    suspend fun fetchLinks(startsWith: String? = null, paginated: Int? = null, page: Int = 1, perPage: Int = 25): Map<String, Link> {
        val linksList: LinksWrapper = client.get(buildUrl(ENDPOINT_LINKS), requestBuilder {
            parameter("starts_with", startsWith)
            parameter("paginated", paginated)
            parameter("page", page)
            parameter("per_page", perPage)
        })
        return linksList.links ?: emptyMap()
    }

    /**
     * Returns an array of tag objects of one space. Use the version parameter and the correct token types to receive either draft and published or only published links.
     *
     * [startsWith] Filter by full_slug. Can be used to retrieve all tags form a specific folder. Examples: starts_with=de/beitraege, starts_with=en/posts
     */
    suspend fun fetchTags(startsWith: String? = null): List<Tag> {
        val tagsList: TagsWrapper = client.get(buildUrl(ENDPOINT_TAGS), requestBuilder {
            parameter("starts_with", startsWith)
        })
        return tagsList.tags ?: emptyList()
    }

    private fun buildUrl(method: String, vararg pathSegments: String): Url {
        return URLBuilder().apply {
            this.protocol = apiProtocol
            this.host = apiEndpoint
            this.port = apiPort
            this.path(apiVersion, "cdn", method, *pathSegments)
        }.build()
    }

    private companion object {
        private val API_PROTOCOL = URLProtocol.HTTPS
        private const val API_ENDPOINT = "api.storyblok.com"
        private const val API_VERSION = "v2"

        private const val SDK_VERSION = "1.0.1"
        private const val SDK_USER_AGENT = "storyblok-sdk-android/$SDK_VERSION"

        private const val VERSION_PUBLISHED = "published"
        private const val VERSION_DRAFT = "draft"

        private const val ENDPOINT_STORIES = "stories" // the base endpoint for stories
        private const val ENDPOINT_SPACE = "spaces/me" // the endpoint for the current space
        private const val ENDPOINT_LINKS = "links" // the endpoint for links
        private const val ENDPOINT_TAGS = "tags" // the endpoint for tags
        private const val ENDPOINT_DATASOURCE = "datasources" // the endpoint for datasources
        private const val ENDPOINT_DATASOURCE_ENTRIES = "datasource_entries" // the endpoint for datasource entries
    }
}