<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML>
<html>
<head>
    <meta charset="utf-8">
</head>
<body>
<h1>Hangman Management</h1>

<div>
    <table id="management" width="60%" border="1px">
        <thead>
        <tr>
            <th width="20%">
                ID
            </th>
            <th width="30%">
                WORD
            </th>
            <th width="30%">
                GUESSED WORD
            </th>
            <th width="20%">
                ERRORS
            </th>
        </tr>
        </thead>
        <tbody>

        </tbody>
        <tfoot>
        <tr>
            <td colspan="4"></td>
        </tr>
        </tfoot>

    </table>
</div>
<script type="application/javascript" src="resources/js/jquery-1.11.0.min.js"></script>
<script type="application/javascript" src="resources/js/management.js"></script>
</body>
</html>