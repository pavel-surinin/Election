var MultiResultRowModalRowComponent = React.createClass({
  componentDidMount: function () {
    $(this.refs.namep).tooltip();
  },
  onInputChange : function(event){
    var pid = this.props.info.partijosId;
    var cid = this.props.info.id;
    var points = event.target.value;
    this.props.onChangePartyRating(pid,cid,points);
  },
  render: function() {
    var info = this.props.info;
    return (
      <div>
        <div style={{float : 'left',padding:'2px'}} className="input-group col-xs-6 col-sm-3 col-lg-2">
          <span ref='namep' title={info.name + ' ' + info.surname} className="input-group-addon">{info.numberInParty}</span>
          <input
            onChange={this.onInputChange}
            id={'rating-points-input-' + this.props.info.id}
            type="number"
            className="form-control"
          />
        </div>
      </div>
    );
  }

});

window.MultiResultRowModalRowComponent = MultiResultRowModalRowComponent ;
