var DistrictRespesentativeListViewRowComponent = React.createClass({

  render: function() {
    return (
            <tr>

              <td className="small">
                {this.props.name}
              </td>
              <td className="small">
                {this.props.surname}
              </td>
              <td className="small">
                {this.props.districtName}
              </td>

            </tr>
    );
  }

});

window.DistrictRespesentativeListViewRowComponent = DistrictRespesentativeListViewRowComponent;
