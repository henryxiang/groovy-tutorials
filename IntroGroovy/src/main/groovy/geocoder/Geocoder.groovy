/* ===================================================
 * Copyright 2012 Kousen IT, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ========================================================== */
package geocoder

class Geocoder {
    public static final String BASE =
            'https://maps.google.com/maps/api/geocode/xml?'
    public static final String KEY = 'AIzaSyDqSBi5G5qelDmUzgu7slWfldbHfT2WP2M'

    @SuppressWarnings("GrMethodMayBeStatic")
    void fillInLatLng(Stadium stadium) {
        String encoded =
            [stadium.street, stadium.city, stadium.state].collect { 
                URLEncoder.encode(it, 'UTF-8')
            }.join(',')
        String qs = "address=$encoded&key=$KEY"
        String url = "$BASE$qs"
        println url
        def response = new XmlSlurper().parse(url)
        def loc = response.result[0].geometry.location
        stadium.latitude = loc.lat.toDouble()
        stadium.longitude = loc.lng.toDouble()
    }
}
