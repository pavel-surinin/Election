var MultiResultComponent = React.createClass({
  render: function() {
    var parties = [];
    this.props.list.map(function(p){
      parties.push(
      <div className="form-group panel-body" style={styles.marginTable}>
        <div className="col-md-2 small">
          <h5>{p.partyNumber}</h5>
        </div>
        <div className="col-md-5 small">
          <h5>{p.name}</h5>
        </div>
        <div className="input-group col-md-3 small">
          <input type="number" className="form-control" placeholder="Balsų skaičius" aria-describedby="basic-addon2"/>
          <span className="input-group-addon" id="basic-addon2">vnt.</span>
        </div>
      </div>
    );
    });

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
          <form>
						{parties}

						<div className="form-group panel-footer">
							<div className="col-md-6">
								<h4><b>Sugadinti balsai:</b></h4>
							</div>
							<div className="input-group col-md-3 small">
								<input type="number" className="form-control" placeholder="Balsų skaičius" aria-describedby="basic-addon2" required/>
								<span className="input-group-addon" id="basic-addon2">vnt.</span>
							</div>
						</div>

          </form>
        </div>
        <div className="col-md-2 pull-right">
          <button type="submit" className="btn btn-primary">Submit</button>
        </div>
      </div>
    );
  }
});

window.MultiResultComponent = MultiResultComponent;
