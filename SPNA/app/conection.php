<?php
	$dsn = "mysql:host=localhost;dbname=id10166616_spna;charset=utf8";
	$user = "id10166616_root";
	$pwd = "katami123";

	try {
		$PDO = new PDO($dsn,$user,$pwd);
		echo "Connectado com sucesso";
	} catch (PDOException $erro) {
		echo "Ocorreu um erro";
	}
?>