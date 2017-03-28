/*<![CDATA[*/
var main = function(){
    $("#loadZip").on("click", function () {
        var cep = $("#zip").val();
        var data = JSON.stringify({cep: cep});
        var url;

        if(window.location.href.indexOf("edit") !=-1){
            url = "../../ws/cep-address";
        }else{
            url = "../ws/cep-address";
        }

        jQuery.ajax({
            url: url,
            type: "POST",
            data: data,
            dataType: "json",
            contentType: "application/json; charset=utf-8",
            success: function(data){
                $("#state").val(data.state);
                $("#street").val(data.street);
                $("#city").val(data.city);
                $("#district").val(data.district);
                $("#zip").val(data.zip);
            },
            error: function(xhr, textStatus, errorThrown) {
                alert(xhr.responseJSON.message);
            }
        });
    });
};
window.onload = function() {
  main();
};
/*]]>*/