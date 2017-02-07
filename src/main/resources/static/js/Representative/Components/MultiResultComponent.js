var MultiResultComponent = React.createClass({
  render: function() {
    var self = this;
    var parties = [];
    this.props.list.map(function(p,index){
      parties.push(
      <MultiResultRowComponent registerVotes={self.props.registerVotes} key={index} party={p} />
    );
    });
    var errorMesages = validation.showMsg(this.props.errorMesages);
    return (
      <div>
        <div className="panel panel-default col-md-12">
          <div className="row panel-heading">
            <div className="col-md-2">
              <h4>Nr.</h4>
            </div>
            <div className="col-md-5">
              <h4>Partija</h4>
            </div>
            <div className="col-md-5">
              <h4>Balsu skaicius</h4>
            </div>
          </div>
          <form onSubmit={this.props.onHandleSubmit}>
            <br/>
            {parties}
            <br/>
            <div className="form-group panel-footer">
              <div className="col-md-7">
                <h4><b>Sugadinti balsai:</b></h4>
              </div>
              <div className="input-group col-md-3 small">
                <input onChange={this.props.onHandleSpoiledChange} type="number" className="form-control" placeholder="Balsų skaičius" aria-describedby="basic-addon2" required/>
                <span className="input-group-addon" id="basic-addon2">vnt.</span>
              </div>
              <br/>
              <button type="submit" className="btn btn-primary">Submit</button>
            </div>
            {errorMesages}
          </form>
        </div>
      </div>
    );
  }
});

window.MultiResultComponent = MultiResultComponent;
