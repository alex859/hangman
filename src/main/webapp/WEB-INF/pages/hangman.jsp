<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML>
<html>
<head>
    <meta charset="utf-8">
</head>
<body>
<h1>Hangman!</h1>

<div>
    <p id="levelSelectP">
        Select your level:
        <select id="levelSelect" name="level">
            <option value="0">Select level</option>
            <c:forEach items="${levels}" var="level">
                <option value="${level}">${level}</option>
            </c:forEach>
        </select>
    </p>
    <p>
        <button id="newGame">New game</button>
    </p>
    <p>
        <button id="stopGame" style="display:none;">Stop game</button>
    </p>
    <p>
        <button id="continueGame" style="display:none;">Continue last game</button>
    </p>
</div>
<div id="game" style="display:none;">
    <p>
        Lives: <span id="lives"></span>
    </p>
    <p>
        Word to guess: <input id="letterTemplate" style="display: none;width: 20px;" type="text" maxlength="1" value=""/>
    </p>
    <p>
        Insert a letter: <input id="myLetter" style="width: 20px;" type="text" maxlength="1" value=""/>
        <button id="submit">Check</button>
    </p>
</div>
<script type="application/javascript" src="resources/js/jquery-1.11.0.min.js"></script>
<script type="application/javascript" src="resources/js/jquery.cookie.js"></script>
<script type="application/javascript" src="resources/js/hangman.js"></script>
</body>
</html>