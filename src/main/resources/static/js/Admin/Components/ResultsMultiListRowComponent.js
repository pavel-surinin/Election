var ResultsMultiListRowComponent = React.createClass({
  approve : function(){
    this.props.onHandleApprove(this.props.info.id,'multi');
  },
  delete : function(){
    this.props.onHandleDelete(this.props.info.id,'multi');
  },
  render: function() {
    return (
      <tr>
        <td className="small">
          {this.props.info.name}
        </td>
        <td className="small">
          {this.props.info.representativeName}
        </td>
        <td className="small">
          {this.props.info.countyName}
        </td>
        <td className="small">
          <div>
            &nbsp;
              <button onClick={this.approve} data-toggle="tooltip1" title="Patvirtinti" type="button" className="btn btn-success btn-sm fa fa-check"></button>
            &nbsp;
              <button onClick={this.delete} data-toggle="tooltip1" title="Ištrinti" type="button" className="btn btn-danger btn-sm fa fa-trash"></button>
          </div>
        </td>
      </tr>
    );
  }

});

window.ResultsMultiListRowComponent = ResultsMultiListRowComponent;
