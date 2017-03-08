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
    $(this.refs.deny).tooltip();
  },
  approve : function(){
    this.props.onHandleApprove(this.props.info.id,'multi',this.props.info.name);
  },
  delete : function(){
    this.props.onHandleDelete(this.props.info.id,'multi',this.props.info.name);
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
                id={'confirm-button-' + this.props.info.id}
                type="button"
                className="btn btn-success btn-sm fa fa-check"
              ></button>
            &nbsp;
            <button ref='deny'
                    data-target={"#confirmationModal" + this.props.info.type + this.props.info.id}
                    title="Ištrinti" type="button" id={'delete-button-' + this.props.info.id}
                    className="btn btn-danger btn-sm fa fa-trash" data-toggle="modal"
            ></button>
            <div id={'confirmationModal' + this.props.info.type + this.props.info.id} className="modal fade" role="dialog">
              <div className="modal-dialog">
                <div className="modal-content">
                  <div className="modal-header">
                    <button type="button" id="modal-close-button" className="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                  </div>
                  <div className="modal-body">
                    <h5 className="modal-title">Ar tikrai norite ištrinti {this.props.info.name} daugiamandatės apygardos rezultatus?</h5>
                  </div>
                  <div className="modal-footer">
                    <div className="btn-group">
                        <button
                          ref='delete'
                          onClick={this.delete}
                          data-toggle="tooltip1"
                          title="Ištrinti"
                          type="button"
                          className="btn btn-default"
                          data-dismiss="modal">Taip
                        </button>
                        <button type="button" id="close-button"  className="btn btn-default" data-dismiss="modal">Ne</button>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </td>
      </tr>
    );
  }

});

window.ResultsMultiListRowComponent = ResultsMultiListRowComponent;
