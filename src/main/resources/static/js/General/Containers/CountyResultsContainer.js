/**
 * Created by nevyt on 2/21/2017.
 */
function getCountyInfo(self,id){
    axios
        .get("/county/" + id)
        .then(function(response){
        self.setState({
            county: response.data,
            isLoading: false,
        });
    })
}
function getCountyResults(self, id){
    axios.get('/results/county/' + id)
        .then(function(response){
            self.setState({
                results: response.data,
            });
        })
}
var CountyResultsContainer  = React.createClass({
    getInitialState: function() {
        return {
            results:[],
            county:[],
            isLoading:true,
        };
    },
    componentDidMount: function() {
        getCountyInfo(this,this.props.params.county);
        getCountyResults(this,this.props.params.county);

    },
    componentWillReceiveProps: function(nextProps) {
        getCountyInfo(this, nextProps.params.county);
        getCountyResults(this,this.props.params.county);
    },
    render: function() {
        if (this.state.isLoading) {
          return <div style={{marginTop : '3px', color : 'white', textAlign : 'center'}}><i className="fa fa-circle-o-notch fa-spin fa-3x fa-fw"></i></div>;
        }
        return (
            <CountyResultsComponent
                canvasBar={<canvas id='barC'></canvas>}
                county={this.state.county}
                countyId={this.props.params.county}
                results={this.state.results}
            />
        );
    }

});
window.CountyResultsContainer = CountyResultsContainer;
