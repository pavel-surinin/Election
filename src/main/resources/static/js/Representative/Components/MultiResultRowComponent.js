var MultiResultRowComponent = React.createClass({
  onInputChange : function(event){
    var votes = event.target.value;
    var pid = this.props.party.id;
    this.props.registerVotes(pid, votes);
  },
  render: function() {
    var p = this.props.party;
    return (
      <div className="row text-primary">
        <div className="col-md-2 small">
          <h5>{p.partyNumber}</h5>
        </div>
        <div className="col-md-5 small">
          <h5>{p.name}</h5>
        </div>
        <div className="input-group col-md-3 small">
          <input onChange={this.onInputChange} type="number" className="form-control" placeholder="Balsų skaičius" aria-describedby="basic-addon2" required/>
          <span className="input-group-addon" id="basic-addon2">vnt.</span>
        </div>
      </div>
    );
  }

});

window.MultiResultRowComponent = MultiResultRowComponent;
