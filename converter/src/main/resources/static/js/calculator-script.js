var plnInput = document.getElementById("price-pln");
var otherInput = document.getElementById("price-other");

plnInput.onchange = function () {
	if (this.value != "" || this.value.length > 0) {
		document.getElementById("price-other").disabled = true;
	} else{
		document.getElementById("price-other").disabled = false;
	}	  
}

otherInput.onchange = function () {
	if (this.value != "" || this.value.length > 0) {
		document.getElementById("price-pln").disabled = true;
	} else{
		document.getElementById("price-pln").disabled = false;
	}	  
}