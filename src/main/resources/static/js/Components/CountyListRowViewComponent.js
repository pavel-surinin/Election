var CountyListRowViewComponent = React.createClass({

  render: function() {
    return (
            <tr>
              <td>
                {this.props.id}
              </td>
              <td>
                {this.props.name}
              </td>
              <td>
              <a href="#/county/details"><button type="button" className="btn btn-success btn-sm">Žiūrėti</button></a>
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
