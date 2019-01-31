let name = document.forms["inputs"]["name"].value;
let surname = document.forms["inputs"]["surname"].value;
let studentClass = document.forms["inputs"]["studentClass"].value;
let birthDate = document.forms["inputs"]["birthDate"].value;
let studyBegan = document.forms["inputs"]["studyBegan"].value;
let email = document.forms["inputs"]["mail"].value;

function validateForm() {

  let name = document.forms["inputs"]["name"].value;
  let surname = document.forms["inputs"]["surname"].value;
  let studentClass = document.forms["inputs"]["studentClass"].value;
  let birthDate = document.forms["inputs"]["birthDate"].value;
  let studyBegan = document.forms["inputs"]["studyBegan"].value;
  let email = document.forms["inputs"]["mail"].value;
  document.getElementById("btSubmit").setAttribute("disabled","disabled");
  const nameReg = /[A-Z][a-z]+/;
  const classReg = /[1-5]/;
  const dateReg = /[0-3][0-9]\.(0?[1-9]|1[0-12])\.[1900-2002]/;
  const mailReg = /[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\.[a-zA-Z0-9]+/;
  
  if (!nameReg.test(name)){
    alert("Wrong Name type. Please enter name and surname.");
  }

  if(name != "" || surname != "" || email != "" || studentClass != "" || birthDate != "" || studyBegan != ""){
    document.getElementById("btSubmit").removeAttribute("disabled");
  }

}

   
  function DoOnSubmit(){
    if(name == "" || surname == "" || email == "" || studentClass == "" || birthDate == "" || studyBegan == ""){
      window.alert("All fields must be filled out.");
      return false;
    }
    else{
      window.alert("All informations has been sent");
      document.getElementById("name").value = "";
      document.getElementById("email").value = "";
      document.getElementById("btSubmit").value = "";
      return true;
    }
  }