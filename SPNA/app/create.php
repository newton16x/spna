<?php
	include "conexao.php"

	$name = $_POST['name'];
	$surname = $_POST['nome'];
	$email = $_POST['nome'];
	$password = $_POST['nome'];
	$telemovel = $_POST['nome'];

	$sql_insert = "INSERT INTO User (name,surname,email,password,telemovel) VALUES(:NAME,:SURNAME,:EMAIL,:PASSWORD,:TELEMOVEL)";

	$stmt = $PDO->prepare($sql_insert);

	$stmt->bindParam(':NAME',$name);
	$stmt->bindParam(':SURNAME',$surname);
	$stmt->bindParam(':EMAIL',$email);
	$stmt->bindParam(':PASSWORD',$password);
	$stmt->bindParam(':TELEMOVEL',$telemovel);

	if($stmt->execute()){
		$dados = array("CREATE"=>"OK");
	}else{
		$dados= array("CREATE"=>"ERRO")
	}

	echo json_encode($dados);
?>