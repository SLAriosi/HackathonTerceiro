<?php
$apiUrl = 'http://localhost:3001/api/vacina/';

$deleteUrl = $apiUrl . $id;

$options = [
    'http' => [
        'method' => 'DELETE',
        'header' => "Content-Type: application/json\r\n"
    ]
];
$context = stream_context_create($options);

$response = file_get_contents($deleteUrl, false, $context);

$httpCode = $http_response_header[0];

if (strpos($httpCode, '200') !== false) {
    echo "<script>window.location.href='list/vacina'</script>";
} else {
    echo "<script>window.location.href='list/vacina'</script>";
}
