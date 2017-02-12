function addMinutes(date, minutes) {
  return new Date(date.getTime() + minutes*60000);
}
var ResultsMultiListRowComponent = React.createClass({
  isDeleteActive : function(){
    var time = new Date(this.props.info.votesMultiRegisteredDate);
    console.log(addMinutes(time, 2) - new Date());
    console.log(addMinutes(time, 2));
    console.log((addMinutes(time, 2) - new Date()) < 0);
  },
  componentDidMount: function () {
    $(this.refs.approve).tooltip();
    $(this.refs.delete).tooltip();
    setInterval(1000,(addMinutes(time, 2) - new Date()) < 0);
  },
  approve : function(){
    this.props.onHandleApprove(this.props.info.id,'multi');
  },
  delete : function(){
    this.props.onHandleDelete(this.props.info.id,'multi');
  },
  render: function() {
    this.isDeleteActive();
    return (
      <tr>
        <td onClick={this.isDeleteActive} className="small">
          {this.props.info.name}
        </td>
        <td className="small">
          {this.props.info.representativeName}
        </td>
        <td className="small">
          {this.props.info.votesMultiRegisteredDate}
        </td>
        <td className="small">
          <div>
            &nbsp;
              <button
                ref='approve'
                onClick={this.approve}
                data-toggle="tooltip1"
                title="Patvirtinti"
                type="button"
                className="btn btn-success btn-sm fa fa-check"
                disabled={true}
              ></button>
            &nbsp;
              <button
                ref='delete'
                onClick={this.delete}
                data-toggle="tooltip1"
                title="Ištrinti"
                type="button"
                className="btn btn-danger btn-sm fa fa-trash"
              ></button>
          </div>
        </td>
      </tr>
    );
  }

});

window.ResultsMultiListRowComponent = ResultsMultiListRowComponent;
