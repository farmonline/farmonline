var jQueryScript = document.createElement('script');  
jQueryScript.setAttribute('src','https://maps.googleapis.com/maps/api/js?key=AIzaSyC9Z_D96ex8C6nL6vkJG-jBQWL4uw5FAwg&libraries=places');
document.head.appendChild(jQueryScript);

var map;
var infowindow;

debugger;

var latitude =  0;
var longitude = 0;


//variaveis requisao client-side
var outputlat = '';
var outputlng = '';

//variaveis requisao server-side
var outputlatserver = '';
var outputlngserver = '';

outputlatserver = document.getElementById('latitudeserver');
outputlngserver = document.getElementById('longitudeserver');


$(window).load(function(){	
	 outputlat = document.getElementById('latitude');
	 outputlng = document.getElementById('longitude');
	 
	 if (navigator.geolocation) {
	        navigator.geolocation.getCurrentPosition(showPosition);
	    } 
	 
	 function showPosition(position) {

		    //retorno a latitude e longitude no load da pagina
		    //a partir da sua localização atual
		    lat = position.coords.latitude;
		    console.log(outputlat);
		    console.log(outputlng);
		    outputlat.value = position.coords.latitude;    
		    outputlng.value = position.coords.longitude;   
			initMap();
		}
	

  });



//função chamada pelo server autilizando a geolocalização pelo endereço digitado
function RefreshMap()
{		
   alert('PRESSIONE ENTER');
	
	var latserver =  Number(outputlatserver.value);    
    var lngserver =  Number(outputlngserver.value);      
	
	var pyrmont = {			
		latserver,
		lngserver 
	};
	
	 var request = {
			    location: pyrmont,
			    radius: '500',
			    query: 'farmacia'
			  };

	map = new google.maps.Map(document.getElementById('map'), {
		center : pyrmont,
		zoom : 15
	});

	infowindow = new google.maps.InfoWindow();
	var service = new google.maps.places.PlacesService(map);
	service.textSearch(request, callback);
}

//função apenas utilizada no load da pagina
function initMap()
{		
    var lat =  Number(outputlat.value);    
    var lng = Number(outputlng.value);      
	
	var pyrmont = {			
		lat,
		lng 
	};
	
	 var request = {
			    location: pyrmont,
			    radius: '500',
			    query: 'farmacia'
			  };

	map = new google.maps.Map(document.getElementById('map'), {
		center : pyrmont,
		zoom : 15
	});

	infowindow = new google.maps.InfoWindow();
	var service = new google.maps.places.PlacesService(map);
	service.textSearch(request, callback);
}

function callback(results, status) {
	
	if (status === google.maps.places.PlacesServiceStatus.OK) {
		for (var i = 0; i < results.length; i++) {
			createMarker(results[i]);
		}
	}
}

function createMarker(place) {
	var placeLoc = place.geometry.location;
	var marker = new google.maps.Marker({
		map : map,
		position : place.geometry.location
	});

	google.maps.event.addListener(marker, 'click', function() {
		infowindow.setContent(place.name);
		infowindow.open(map, this);
	});
}