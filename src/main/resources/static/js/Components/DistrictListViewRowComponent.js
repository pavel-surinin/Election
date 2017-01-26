var DistrictListViewRowComponent = React.createClass({
  onHandleDeleteClick : function(){
    this.props.onHandleDelete(this.props.id);
  } ,
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
                  <button type="button" className="btn btn-primary" aria-label="Left Align">
                      Redaguoti
                    </button>
                    <button onClick={this.onHandleDeleteClick} type="button" className="btn btn-danger" aria-label="Left Align">
                      Šalinti
                    </button>
               </div>
              </td>
            </tr>
    );
  }
});

window.DistrictListViewRowComponent = DistrictListViewRowComponent;
