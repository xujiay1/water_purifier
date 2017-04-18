<?php
$url = 'https://spreadsheets.google.com/feeds/list/1YzyGBddF_ogqC61_s6hZBv82YOE3KUbbR6qAbd2Dxs4/1/public/basic?alt=json';
$file= file_get_contents($url);
$json = json_decode($file);
$rows = $json->{'feed'}->{'entry'};
foreach($rows as $row) {
  $name = $row->{'title'}->{'$t'};
  $title = $row->{'content'}->{'$t'};
  echo $name;
  echo '|';
  echo $title;
  echo ';';
}
?>
