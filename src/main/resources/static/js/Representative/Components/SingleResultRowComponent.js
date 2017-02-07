var SingleResultRowComponent = React.createClass({
  onInputChange : function(event){
    var votes = event.target.value;
    var cid = this.props.candidate.id;
    this.props.registerVotes(cid, votes);
  },
  render: function() {
    var p = this.props.candidate;
    return (
      <div className="form-group panel-body" style={styles.marginTable}>
        <div className="col-md-6 small">
          <h5>{p.name} {p.surname}</h5>
        </div>
        <div className="input-group col-md-3 small">
          <input onChange={this.onInputChange} type="number" className="form-control" placeholder="Balsų skaičius" aria-describedby="basic-addon2" required/>
          <span className="input-group-addon" id="basic-addon2">vnt.</span>
        </div>
      </div>
    );
  }

});

window.SingleResultRowComponent = SingleResultRowComponent;
