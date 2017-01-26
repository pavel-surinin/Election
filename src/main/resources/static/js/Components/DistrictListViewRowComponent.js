var DistrictListViewRowComponent = React.createClass({
  onHandleDeleteClick: function () {
    this.props.onHandleDelete(this.props.id);
  },
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
                {this.props.adress}
              </td>
              <td>
                {this.props.countyName}
              </td>
              <td>
                {this.props.representativeName}
              </td>
              <td>
                <div>
                    &nbsp;
                    <button type="button" onClick={this.edit} className="btn btn-primary" aria-label="Left Align">
                      Redaguoti
                    </button>
                    &nbsp;
                    <a className="btn btn-danger" onClick={this.onHandleDeleteClick}>  <i className="fa fa-trash-o fa-lg"></i> Ištrinti</a>

               </div>
              </td>
            </tr>
    );
  }
});

window.DistrictListViewRowComponent = DistrictListViewRowComponent;
