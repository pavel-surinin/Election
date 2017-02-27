var MultiResultRowModalRowComponent = React.createClass({
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
        <div style={{float : 'left',paddingTop:'10px',paddingRight:'2px',paddingLeft:'2px'}} className="col-xs-6 col-sm-4 col-lg-3">
          <div>
          {info.numberInParty + '. ' + info.name + ' ' + info.surname}
          </div>
          <div>
          <input
            onChange={this.onInputChange}
            id={'rating-points-input-' + this.props.info.id}
            type="number"
            className="form-control"
          />
          </div>
        </div>
      </div>
    );
  }

});

window.MultiResultRowModalRowComponent = MultiResultRowModalRowComponent ;
