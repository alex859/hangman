/**
 * Hangman JS
 */
var url = 'rest/hangman';
$(document).ready(function () {

    //check if there is already a game
    var id=getHangmanIdFromCookie();
    if (id) {
        //I can continue
        $('#continueGame').show();
    }

    $('#newGame').click(function () {
        //clear the cookie if present
        $.removeCookie('hangman');
        var level = $('#levelSelect').val();
        if (level != '0') {
            getNewHangman(level);
        } else {
            alert('Please select a level.')
        }
    });

    $('#continueGame').click(function () {
        resetLevel();
        getHangman();
    });

    $('#stopGame').click(function () {
        cleanGame();
    });

    $('#submit').click(function () {
        check();
    });

    //submit by pressing enter
    $(document).keypress(function (e) {
        if (e.which == 13) {
            check();
        }
    });
});

/**
 * Asks the server for a new hangman game
 */
function getNewHangman(level) {

    var get=$.get(url, {level:level})
        .done(function (data) {
            play(data);
        })
        .fail(function () {
            alert("Error");
        })
        .always(function () {

        });
    startPlaying(get);

}

/**
 * Asks the server for an existing a hangman game
 */
function getHangman() {
    var id = getHangmanIdFromCookie();
    var get=$.get(url+'/'+id);
    startPlaying(get);
}

/**
 * Start or continue game
 * @param o
 */
function startPlaying(o){
    o.done(function (data) {
        play(data);
    })
        .fail(function () {
            alert("Error");
        })
        .always(function () {

        });
}

/**
 *  Check if the letter is guessed or not
 */
function check() {
    var $myLetter = $('#myLetter');
    var letter = $myLetter.val();
    var id = getHangmanIdFromCookie();
    if (letter != '') {
        //submit the letter
        $.post(url+'/'+id, { letter: letter})
            .done(function (data) {
                var win = data.lettersNeeded == 0;
                var lose = data.lives == 0;
                if (win || lose) {
                    if (win) {
                        //you win
                        alert('You Win!!! The word was: ' + data.guessedWord);
                    } else {
                        //you lose
                        alert('You lose!');
                    }
                    //game is over
                    cleanGame();
                } else {
                    switch (data.lastResult) {
                        case 'GUESSED':
                            alert('Good you guessed!!!');
                            break;
                        case 'NOT_GUESSED':
                            alert('Bad answer.');
                            break;
                        case 'ALREADY_GUESSED':
                            alert('Already guessed letter.');
                            break;
                    }
                    play(data);
                }
            })
            .fail(function () {
                alert("Error");
            })
            .always(function () {
                $myLetter.val('');
            });
    }

}

/**
 * Start a new/existing game
 * @param hangman The game to be populated
 */
function play(hangman) {
    //clear previous words
    $('.letter').remove();
    $('#lives').html(hangman.lives);
    var $letterTemplate = $('#letterTemplate');
    var $lastLetter = $letterTemplate;
    //show the guessed word
    for (var i = 0; i < hangman.guessedWord.length; i++) {
        var $letter = $letterTemplate.clone(true);
        $letter.addClass('letter');
        $letter.attr('id', '');
        var letter = hangman.guessedWord[i];
        if (letter != ' ') {
            $letter.val(letter.toUpperCase());
            //if the letter is present we disable the input
            $letter.attr('disabled', 'disabled');
        } else {
            $letter.attr('readonly', 'readonly');
        }
        $lastLetter.after($letter);
        $lastLetter = $letter;
        $letter.show();
    }
    $('#game').show();
    $('#levelSelectP').hide();
    $('#continueGame').hide();
    $('#newGame').hide();
    $('#stopGame').show();
    $('#myLetter').focus();

}

/**
 * Clean the game
 */
function cleanGame() {
    $('.letter').remove();
    $('#game').hide();
    $('#continueGame').hide();
    $('#stopGame').hide();
    $('#newGame').show();
    $('#levelSelectP').show();
    resetLevel();
    var id = getHangmanIdFromCookie();
    $.ajax({url: url+'/'+id, type: 'DELETE'})
        .done(function (data) {
            //clear the cookie if present
            $.removeCookie('hangman');
            alert('Game Over');
        })
        .fail(function () {
            alert("Error");
        })
        .always(function () {

        });

}

function resetLevel() {
    $('#levelSelect [value=0]').attr('selected', 'selected');
}

function getHangmanIdFromCookie(){
    return $.cookie('hangman');
}

