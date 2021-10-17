document.addEventListener("DOMContentLoaded", () => {

    document.querySelectorAll("form").forEach(form => {
        form.addEventListener("submit", function (e){
            e.preventDefault();

            let request = new XMLHttpRequest();
            request.open(e.currentTarget.method, e.currentTarget.action + "?" + new URLSearchParams(new FormData(e.currentTarget)).toString(), true);
            //request.send(new FormData(e.currentTarget));
            request.send();
            request.onload = function() {

                let response = JSON.parse(request.response);

                if( response[0] === "redirect" ) window.location.href = response[1];
                else alert(response[1]);

            };

        });
    })

});