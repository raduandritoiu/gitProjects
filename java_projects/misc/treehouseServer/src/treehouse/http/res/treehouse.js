
var savedCommands = [];
var curIdx = savedCommands.length-1;

function makeColumns(rows) 
{
    var colSet = {};
    var hasId = false;
    var i,j;
    for (i = 0; i < rows.length; i++) {
        var row = rows[i];
        var keys = _.keys(row);
        for (j = 0; j < keys.length; j++)
        {
            if (keys[j] == 'id')
                hasId = true;
            else
                colSet[keys[j]] = keys[j];
        }
    }

    var columns = _.keys(colSet);
    columns.sort();

    return hasId ? ['id'].concat(columns) : columns;
}

function displayEmpty()
{
    $('#rows').html(
        '<tr><th>empty</th></tr>' + 
        '<tr><td>&nbsp;</td></tr>');
    $('#rowCount').html(
        '<span class="badge">0</span> rows.');
}

function displayRows(rows) 
{
    if (rows.length == 0) {
        displayEmpty();
    }
    else {
        var cols = makeColumns(rows);

        // header
        var html = '<tr>';
        var i,j;
        for (i = 0; i < cols.length; i++)
            html += '<th nowrap>' + cols[i] + '</th>';
        html += '</tr>';

        // rows
        for (i = 0; i < rows.length; i++) {
            row = rows[i];
            html += '<tr>';
            for (j = 0; j < cols.length; j++) {
                var val = row[cols[j]];
                if (val) {
                    if (typeof val === 'object')
                        html += '<td nowrap>' + JSON.stringify(val).substring(0, 50) + '</td>';
                    else
                        html += '<td nowrap>' + val + '</td>';
                }
                else
                    html += '<td></td>';
            }
            html += '</tr>';
        }

        $('#rows').html(html);

        $('#rowCount').html(
            '<span class="badge">' + rows.length + '</span> ' + 
            ((rows.length == 1) ? 'row.' : 'rows.'));
    }
}

/**
  * commandSucceeded
  */
function commandSucceeded(result)
{
    if (typeof result === 'object') {
        if (result instanceof Array) {
            displayRows(result);
        }
        else {
            displayRows([result]);
        }
    }
    else {
        displayRows([{'val':result}]);
    }
}

/**
  * commandFailed
  */
function commandFailed(jqXHR, textStatus, errorThrown)
{
    displayEmpty();
    alert(errorThrown + '\n\n' + JSON.stringify(jqXHR));
}

$(document).ready(function()
{
    $('#command').keydown(function (e) {

        // enter
        if (e.which == 13) {

            var str = $('#command').val();
            if (str != '') {

                // save the command
                if ((savedCommands.length == 0) || 
                    (savedCommands[savedCommands.length-1] != str))
                {
                    savedCommands.push(str);
                    curIdx = savedCommands.length-1;
                }

                // clear the input element
                $('#command').val("");

                // invoke the command
                var n = str.indexOf(' ');
                var url = str.substring(0,n);
                var params = JSON.parse(str.substring(n+1));

                $.ajax({
                    type: 'POST',
                    url: url,
                    data: JSON.stringify(params)})
                .fail(commandFailed)
                .done(commandSucceeded);
            }
        }
        // up
        else if (e.which == 38) {
            event.preventDefault();

            if (savedCommands.length > 0) {
                if (curIdx == 0) 
                    $('#command').val(savedCommands[curIdx]);
                else
                    $('#command').val(savedCommands[curIdx--]);
            }
        }
        // down
        else if (e.which == 40) {
            event.preventDefault();

            if (savedCommands.length > 0) {
                var last = savedCommands.length - 1;
                if (curIdx == last) 
                    $('#command').val(savedCommands[curIdx]);
                else
                    $('#command').val(savedCommands[++curIdx]);
            }
        }
    });
});

