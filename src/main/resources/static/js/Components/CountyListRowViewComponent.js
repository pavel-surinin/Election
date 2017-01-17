var CountyListRowViewComponent = React.createClass({

  render: function() {
    return (
            <tr>
              <td>
                {this.props.nr}
              </td>
              <td>
                {this.props.countyName}
              </td>
              <td>
                <button type="button" className="btn btn-primary btn-sm">Naujinti</button>
                <button type="button" className="btn btn-danger btn-sm">Trinti</button>
              </td>
            </tr>
    );
  }

});

window.CountyListRowViewComponent = CountyListRowViewComponent;
