var ResultsSingleListRowComponent = React.createClass({

  render: function() {
    return (
      <tr>
        <td>
          Numeris 1
        </td>
        <td>
          Vilniaus
        </td>
        <td>
          Rezultatai
        </td>
        <td>
          <div>
              &nbsp;
              <button ref="confirm" data-toggle="tooltip1" title="Patvirtinti" type="button" className="btn btn-success btn-sm fa fa-check"></button>
              &nbsp;
              <button ref="delete" data-toggle="tooltip1" title="Ištrinti" type="button" className="btn btn-danger btn-sm fa fa-trash"></button>
          </div>
        </td>
      </tr>
    );
  }

});

window.ResultsSingleListRowComponent = ResultsSingleListRowComponent;
