var SingleResultComponent = React.createClass({
  mapCandidatesList : function(){
    var list = [];
    this.props.list.map(function(c){
      list.push(c.name);
    });
    return list;
  },
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
          <form>
            {list}
            <div className="form-group panel-footer" style={styles.marginTable}>
              <div className="col-md-6">
                <h4><b>Sugadinti balsai:</b></h4>
              </div>
              <div className="input-group col-md-3 small">
                <input type="number" className="form-control" placeholder="Balsų skaičius" aria-describedby="basic-addon2" required/>
                <span className="input-group-addon" id="basic-addon2">vnt.</span>
              </div>
            </div>
            <div className="col-md-2 pull-right">
              <button type="submit" className="btn btn-primary">Submit</button>
            </div>
          </form>
        </div>

      </div>
    );
  }
});

window.SingleResultComponent = SingleResultComponent;
