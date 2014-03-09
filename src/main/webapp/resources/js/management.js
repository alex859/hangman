/**
 * Management JS
 */
var url = 'rest/hangmen';
$(document).ready(function () {

    $.get(url)
        .done(function (data) {
            populateManagement(data);
        })
        .fail(function () {
            alert("Error");
        })
        .always(function () {

        });


});

function populateManagement(data){
    for(var i=0;i<data.length;i++){
        var hangman=data[i];
        var $tr=$('<tr></tr>');
        $tr.append($('<td></td>').text(hangman.id));
        $tr.append($('<td></td>').text(hangman.word));
        $tr.append($('<td></td>').text(hangman.guessedWord));
        $tr.append($('<td></td>').text(hangman.errors));
        $('#management tbody').append($tr);

    }
    $('#management tfoot tr td').text('Total games: '+data.length);
}
