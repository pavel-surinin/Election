var CountyDetailDistrictComponent = React.createClass({

  render: function() {
    return (
            <tr>
              <td>
                {this.props.name}
              </td>
              <td>
                {this.props.representative}
              </td>
            </tr>
    );
  }

});

window.CountyDetailDistrictComponent = CountyDetailDistrictComponent;
