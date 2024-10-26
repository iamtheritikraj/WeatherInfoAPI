
<h1>Weather Information API</h1>

<p>This project is a RESTful API built with Spring Boot that retrieves weather information based on a given pincode and date. It minimizes API calls by storing latitude, longitude, and weather data in an H2 in-memory database. The API interacts with the OpenWeather API to fetch the current weather data.</p>


<h2>Features</h2>
<ul>
    <li>Fetch weather information by pincode and date.</li>
    <li>Convert temperature from Kelvin to Celsius before saving.</li>
    <li>Store latitude and longitude associated with each pincode.</li>
    <li>Store weather data to minimize subsequent API calls.</li>
    <li>H2 in-memory database for development and testing.</li>
</ul>

<h2>Technologies Used</h2>
<ul>
    <li>Java</li>
    <li>Spring Boot</li>
    <li>Spring Data JPA</li>
    <li>H2 Database (in-memory)</li>
    <li>RestTemplate for HTTP requests</li>
    <li>OpenWeather API for weather data</li>
</ul>

<h2>Prerequisites</h2>
<p>Make sure you have the following installed:</p>
<ul>
    <li>JDK 17 or higher</li>
    <li>Maven</li>
    <li>An IDE (e.g., IntelliJ IDEA, Eclipse)</li>
</ul>

<h2>Setup Instructions</h2>
<ol>
    <li><strong>Clone the repository:</strong>
        <pre>
            git clone &lt;repository-url&gt;
            cd weather-info-api
        </pre>
    </li>
    <li><strong>Update <code>application.properties</code>:</strong>
        <p>Create or update the <code>src/main/resources/application.properties</code> file to include your OpenWeather API key:</p>
        <pre>
            openweather.api.key=YOUR_API_KEY
        </pre>
    </li>
    <li><strong>Build the project:</strong>
        <pre>
            mvn clean install
        </pre>
    </li>
</ol>

<h2>API Endpoints</h2>

<h3>Get Weather Information</h3>
<ul>
    <li><strong>Endpoint:</strong> <code>/weather</code></li>
    <li><strong>Method:</strong> <code>POST</code></li>
    <li><strong>Request Body:</strong>
        <pre>
            {
                "pincode": "411014",
                "forDate": "2020-10-15"
            }
        </pre>
    </li>
    <li><strong>Response:</strong>
        <p>Returns <code>WeatherInfo</code> object containing:</p>
        <ul>
            <li>Temperature in Celsius</li>
            <li>Humidity</li>
            <li>Pressure</li>
            <li>Weather description</li>
        </ul>
    </li>
</ul>

<h3>Example Request</h3>
<p>Using Postman or cURL, you can send a request like this:</p>
<pre>
curl -X POST http://localhost:8080/weather -H "Content-Type: application/json" -d '{"pincode":"411014","forDate":"2020-10-15"}'
</pre>

<h2>Running the Application</h2>
<p>To run the application, use the following command:</p>
<pre>
mvn spring-boot:run
</pre>
<p>The application will start on <code>http://localhost:8080</code>.</p>


<h2 id="license">License</h2>
<p>This project is licensed under the MIT License - see the <a href="LICENSE">LICENSE</a> file for details.</p>
