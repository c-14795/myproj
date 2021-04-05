$(document).ready(function(){
$("#btn-input").val("")

    $("#btn-chat").click(function(){
	var userMessage = $("#btn-input").val()
	var formattedUserMessage = getUserMessage(userMessage)
	$("#chatbox").append(formattedUserMessage)
	processRequest(userMessage)
	$("#btn-input").val("")
    });
});


function getUserMessage(ms)
{

var message ="<li class=\"right clearfix\"><span class=\"chat-img pull-right\">" +
                "                            <img src=\"http://placehold.it/50/FA6F57/fff&text=U\" alt=\"User Avatar\" class=\"img-circle\" />" +
                "                        </span>" +
                "                            <div class=\"chat-body clearfix\">" +
                "                                <div class=\"header\">" +
                "                                    <small class=\" text-muted\"><span class=\"glyphicon glyphicon-time\"></span>"+new Date()+"</small>" +
                "                                    <strong class=\"pull-right primary-font\">You</strong>\n" +
                "                                </div>" +
                "                                <p>" +ms+
                "                                </p>" +
                "                            </div>" +
                "                        </li>";
return message;
}

function getAssistantMessage(ms)
{


 var message=  "<li class=\"left clearfix\"><span class=\"chat-img pull-left\">\n" +
                "                            <img src=\"http://placehold.it/50/55C1E7/fff&text=ME\" alt=\"User Avatar\" class=\"img-circle\"/>\n" +
                "                        </span>\n" +
                "                                <div class=\"chat-body clearfix\">\n" +
                "                                    <div class=\"header\">\n" +
                "                                        <strong class=\"primary-font\">Assistant</strong>\n" +
                "                                        <small class=\"pull-right text-muted\">\n" +
                "                                            <span class=\"glyphicon glyphicon-time\"></span>"+new Date() +
                "                                        </small>\n" +
                "                                    </div>\n" +
                "                                    <p>\n" +
                                                        ms+
                "                                    </p>\n" +
                "                                </div>\n" +
                "                            </li>"
return message;

}

function processRequest(query){
var resp=''
$.ajax({
        url: 'getAnswer',
        type: 'POST',
        data: JSON.stringify({'query':query}),
        contentType: 'application/json; charset=utf-8',
        dataType: 'json',
        async: true,
        success: function(data) {
                console.log(data);
                resp=data['answer']
                            $("#chatbox").append(getAssistantMessage(resp
	))
            }
        });

}