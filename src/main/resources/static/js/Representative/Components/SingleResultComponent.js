var SingleResultComponent = React.createClass({
  render: function() {
    var list = [];
    var self = this;
    this.props.list.map(function(c, index){
      list.push(
        <SingleResultRowComponent
          key={index}
          candidate={c}
          registerVotes={self.props.registerVotes}
        />
      );
    });
    var errorMesages = validation.showMsg(this.props.errorMesages);
    return (
      <div>
        <div className="panel panel-default col-md-12">
          <div className="row panel-heading">
            <div className="col-md-6">
              <h4>Vardas</h4>
            </div>
            <div className="col-md-6">
              <h4>Balsu skaicius</h4>
            </div>
          </div>
          <form onSubmit={this.props.onHandleSubmit}>
            <br />
            {list}
            <br />
            <div className="form-group panel-footer" style={styles.marginTable}>
              <div className="col-md-6">
                <h4><b>Sugadinti balsai:</b></h4>
              </div>
              <div className="input-group col-md-3 small">
                <input onChange={this.props.onHandleSpoiledChange} type="number" className="form-control" placeholder="Balsų skaičius" aria-describedby="basic-addon2" required/>
                <span className="input-group-addon" id="basic-addon2">vnt.</span>
              </div>
              <br/>
            </div>
              <button type="submit" className="btn btn-primary">Submit</button>
          </form>
          {errorMesages}
        </div>

      </div>
    );
  }
});

window.SingleResultComponent = SingleResultComponent;
