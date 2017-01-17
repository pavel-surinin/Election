var DistrictListViewRowComponent = React.createClass({

  render: function() {
    return (
            <tr>
              <td>
                #
              </td>
              <td>
                {this.props.name}
              </td>
              <td>
                {this.props.county}
              </td>
              <td>
                {this.props.representative}
              </td>
              <td>
                <div>
                	<button type="button" onClick={this.edit} className="btn btn-primary" aria-label="Left Align">
                      Redaguoti
                    </button>
                    <button type="button" onClick={this.delete} className="btn btn-danger" aria-label="Left Align">
                      Šalinti
                    </button>
               </div>
              </td>
            </tr>
    );
  }
});

window.DistrictListViewRowComponent = DistrictListViewRowComponent;